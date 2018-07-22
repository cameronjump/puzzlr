from flask import Flask
from flask import request
from flask import Markup
import cloudinary
import cloudinary.uploader
import cloudinary.api
from helper_functions import ArrayTransformer


app = Flask(__name__)
global board

@app.route("/")
def hello():
    return "You'd not believe how hard this was..."

@app.route("/upload_puzzle_board", methods = ['POST'])
def uploadPuzzleBoard():
    """
    converts images to rgb array, compresses rgb array, sets board variable to the received board array

    @param id: id of board image
    @param num_pieces: number of puzzle pieces of board
    @return: success if all steps are successful, failed otherwise
    """
    global board
    if (request.method == 'POST'):

        result = request.get_json()
        if "id" not in result or "num_pieces" not in result:
            return "MissingKeyError: id or num_pieces do not exist in request object."
        id = result["id"]
        num_pieces = result["num_pieces"]
        url = "https://res.cloudinary.com/puzzlr/image/upload/e_make_transparent:25/e_trim/ar_1:1,c_crop/%s" % id
        print(url)
        transformer = ArrayTransformer()
        board = transformer.setPuzzleBoard(url, num_pieces, 16)
        print("Num of boxes in board: " + str(len(board)))
        print("Number of boxes in box: " + str(len(board[0])))
        print("RGB: " + str(len(board[0][0])))
    else:
        return "RequestError: Request failed because method was not POST."
    return "success"

"""
    This method will:
     * take in a puzzle piece image link
     * download the image from the link cloudinary [X]
     * convert it into an rgb array [X]
     * compress the rgb array
     * make a similary gradient array to see how good it matches with the existing puzzleboard

     @param id: id of puzzle piece image
     @return returns a 2D array of gradients
    """
@app.route("/process_image", methods = ['POST'])
def processImage():
    if (request.method == 'POST'):
        result = request.get_json()
        if "id" not in result or "puzzle_id" not in result or "num_pieces" not in result:
            return "MissingKeyError: id or puzzle_id does not exist in request object."
        img_id = result["id"]
        puzzle_id = result["puzzle_id"]
        num_pieces = result["num_pieces"]

        transformer = ArrayTransformer()
        heat_map = transformer.main(num_pieces, puzzle_id, img_id, 16)
        print(heat_map)
        return "successfully returned heatmap" + str(len(heat_map))


