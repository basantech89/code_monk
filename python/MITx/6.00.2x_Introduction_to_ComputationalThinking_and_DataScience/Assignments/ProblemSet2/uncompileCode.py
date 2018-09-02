import uncompyle2
with open("uncompiled.py", "wb") as fileobj:
    uncompyle2.uncompyle_file("ps2_verify_movement27.pyc", fileobj)
