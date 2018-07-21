import cloudinary
from cloudinary import uploader
import requests

file = '/Users/johnbisognano/downloads/piece.jpg'
cloudinary.uploader.upload(file, public_id="piece")

img_data = requests.get('http://res.cloudinary.com/puzzlr/image/upload/e_make_transparent:25/piece.png').content
with open('piece_transformed.png', 'wb') as handler:
	handler.write(img_data)