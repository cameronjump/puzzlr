# puzzlr
###### _A computer vision puzzle solving app_ 
###### _Made during HackMidwest 2018 24 hour hackathon_ 

User takes picture of puzzle and a piece. These pictures are uploaded to Cloudinary to resize, rotate, trim, and remove the background of the pictures. App then sends request to Flask server deployed on Heroku with address of pictures. Flask server breaks the puzzle into N pieces and breaks each of those pieces into 4x4 grids and averages the colors in each element of the grid. The server converts the uploaded puzzle piece into the same 4x4 grid and averages the colors the same way. The server then compares each orientation of uploaded piece to each of the N pieces in the puzzle using MSE and returns the app a similarity heat map of where the piece matches.

Won 'Most Creative Use of Cloudinary' at HackMidwest 2018

[Cameron Jump](https://github.com/cameronjump/) - Android, UI, Cloudinary APIs

[Visaal Ambalam](https://github.com/visaals/) -  Backend Image Processing, Setup Flask Server on Heroku

[John Bisognano](https://github.com/johnbisognano) - Backend Image Processing, Cloudinary APIs

## Step 1: Upload the image of the entire puzzle and enter the number of puzzle pieces
<p align="center">
<img align="center" src="http://res.cloudinary.com/puzzlr/image/upload/c_scale,q_100,w_200/v1532396480/43086292-9bb5293c-8e62-11e8-8b87-c2b98e4d754d.jpg">
</p>

## Step 2: Upload or take an image of an individual puzzle piece after pressing the piece match button
_For best performance, use a white background and avoid shadows when taking the image._
<p align="center">
<img align="center" src="http://res.cloudinary.com/puzzlr/image/upload/c_scale,q_100,w_200/v1532396459/43086293-9bc205ee-8e62-11e8-8a90-34e88946625d.jpg">
</p>

## Step 3: Our algorithm will show on the heat map where your puzzle piece is most likely to fit 
_Brighter indicates better match_
<p align="center">
<img src="http://res.cloudinary.com/puzzlr/image/upload/c_scale,q_100,w_200/v1532396471/43086291-9ba4fbac-8e62-11e8-9016-cf2f2299604f.jpg">
</p>





