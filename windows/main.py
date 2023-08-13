from moviepy.editor import VideoFileClip
import keyboard
import subprocess
import tkinter as tk
from tkinter import messagebox
import threading
import screeninfo  # 导入获取屏幕分辨率的库

def play_video(filepath, resolution):
    clip = VideoFileClip(filepath)
    clip = clip.resize(resolution)  # 将视频分辨率设置为屏幕分辨率
    clip.preview(fullscreen=True)
    clip.reader.close()
    clip.audio.reader.close_proc()

def lock_keyboard():
    keyboard.block_key('ctrl')
    keyboard.block_key('up')
    keyboard.block_key('down')
    keyboard.block_key('left')
    keyboard.block_key('right')
    keyboard.block_key('enter')
    keyboard.block_key('esc')

def unlock_keyboard():
    keyboard.unblock_key('ctrl')
    keyboard.unblock_key('up')
    keyboard.unblock_key('down')
    keyboard.unblock_key('left')
    keyboard.unblock_key('right')
    keyboard.unblock_key('enter')
    keyboard.unblock_key('esc')

def show_message_box():
    messagebox.showinfo("提示", "观影感受如何？🤣")

def get_screen_resolution():
    screen = screeninfo.get_monitors()[0]
    return screen.width, screen.height

def main():
    filepath = "source/video.mp4"
    screen_resolution = get_screen_resolution()

    lock_keyboard()

    play_thread = threading.Thread(target=play_video, args=(filepath, screen_resolution))
    play_thread.start()

    play_thread.join()

    unlock_keyboard()

    show_message_box()

if __name__ == "__main__":
    main()

