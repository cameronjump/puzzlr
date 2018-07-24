# puzzlr
README WIP

![screenshot_20180722-124056_puzzlr](https://github.com/cameronjump/puzzlr/blob/server/piecematch.jpg)
![screenshot_20180722-124041_puzzlr](https://github.com/cameronjump/puzzlr/blob/server/piece.jpg)
![screenshot_20180722-124052_puzzlr](https://github.com/cameronjump/puzzlr/blob/server/board.jpg)

Computer vision puzzle solving app. User takes picture of puzzle and a piece. These pictures are uploaded to Cloudinary and cropped. App then sends request to Flask server with address of pictures. Flask server breaks the puzzle into pieces and finds average color for small square in each piece. Server then compares the piece sent to each piece in the puzzle and returns the app a similarity heat map of where the piece matches.

Won 'Most Creative Use of Cloudinary' at HackMidwest 2018


[Cameron Jump](https://github.com/cameronjump/)

[Visaal Ambalam](https://github.com/visaals/)

[John Bisognano](https://github.com/johnbisognano)


