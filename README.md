# 2018-10-lego-robotics-hack

October's fun with a Lego Mindstorms NXT robot, this was a 2 hour hack event. We set up a simple 2-wheeled vehicle that used ant-like behavior to follow a path made of blue masking tape.

![Our ant robot in action](robot.gif)

We tried different approaches to trying to find the line once it had been lost, including:

- Looking left (5 degrees) then right (10 degrees) a degree at a time, we found this worked but didn't perform very well due to some duplicate scanning and when coming off the left hand edge of the tape we'd always first look away from the line
- We added an expected search direction, which we tried alternating (assuming we'd come off each edge of the tape alternately, not great) but found that keeping the direction we found the tape in as the next search direction to work better.
- We added a memory of the last 5 directions we found the tape in, and used that to try and guess a probable next correction direction
- We alternated the search pattern so that, rather searching all in one direction first then the other, we would swing back and forth with an increasing arc each time to catch the line if it wasn't where we expected it to be
- Added a small pause between rotation and light reading just to do a little naive debounce on the light sensor reading
- Played with increasing/decreasing the rotation and velocity of the vehicle, we found that making the robot go faster was not always the best way to make it finish the track quicker. This was due to the amount the robot would overshoot the line before it could come to a halt, resulting in more time spent finding the line
- Played with changing the light sensor threshold values to allow the robot to use more of the edge of the tape as part of the line 

Other thoughts we had to improve things:
- Map the line on a 2d array and make assessments about where the next bit of the line will be found
- Use waypoint markers so that if a line were lost we could return to the last known good location
- Use listeners to attempt to update the light level and correct course without necessarily having a "stop and search" solution

Other ideas that would require changing the robot:
- Add a second light sensor so we had an idea of which direction we needed to turn in to find the line again

The above isn't exhaustive, just what I can remember us discussing as I write this the morning after :) Feel free to add things we discussed/ideas as PRs


GIF video made from iPhone footage by installing `ffmpeg`, `imagemagick`, and `gifsicle`, and setting up my OhMyZSH installation to have the `gifify` command [this guy gives](https://gist.github.com/SlexAxton/4989674).
