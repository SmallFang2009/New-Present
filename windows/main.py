from moviepy.editor import VideoFileClip
import keyboard
import subprocess
import tkinter as tk
from tkinter import messagebox
import threading
import screeninfo
import time
from ctypes import cast, POINTER
from comtypes import CLSCTX_ALL
from pycaw.pycaw import AudioUtilities, IAudioEndpointVolume

def play_video(filepath, resolution):
    clip = VideoFileClip(filepath)
    clip = clip.resize(resolution)
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
    messagebox.showinfo("问候", "观影体验如何？🤣")

def get_screen_resolution():
    screen = screeninfo.get_monitors()[0]
    return screen.width, screen.height

def set_volume(volume_level):
    devices = AudioUtilities.GetSpeakers()
    interface = devices.Activate(
        IAudioEndpointVolume._iid_, CLSCTX_ALL, None)
    volume = cast(interface, POINTER(IAudioEndpointVolume))
    volume.SetMasterVolumeLevelScalar(volume_level, None)

def control_volume(max_volume):
    interval = 0.01
    while True:
        set_volume(max_volume)
        time.sleep(interval)

def toggle_mute():
    devices = AudioUtilities.GetSpeakers()
    interface = devices.Activate(
        IAudioEndpointVolume._iid_, CLSCTX_ALL, None)
    volume = cast(interface, POINTER(IAudioEndpointVolume))
    volume.SetMute(0, None)

def main():
    filepath = "source/video.mp4"
    screen_resolution = get_screen_resolution()
    max_volume = 1.0

    lock_keyboard()

    play_thread = threading.Thread(target=play_video, args=(filepath, screen_resolution))
    volume_thread = threading.Thread(target=control_volume, args=(max_volume,))
    mute_thread = threading.Thread(target=toggle_mute)

    play_thread.start()
    volume_thread.start()
    mute_thread.start()

    play_thread.join()

    unlock_keyboard()

    show_message_box()

if __name__ == "__main__":
    main()