from PIL import Image
import requests
from io import BytesIO
import math

response = requests.get('http://res.cloudinary.com/puzzlr/image/upload/e_make_transparent:25/piece.jpg')
im = Image.open(BytesIO(response.content))
pixels = list(im.getdata())
width, height = im.size

# takes list of pixels and image width to make embedded list of rows
def formatImageInto2dArray(width, pixels):
	splitImage = []
	for i in range(0,len(pixels), width):
		splitImage.append(pixels[i:i+(width-1)])
	return splitImage

# averages rgb values to smaller pixel number
# numSquares must be a perfect square
def compress2dArray(square):
	Rtotal = 0
	Gtotal = 0
	Btotal = 0
	numBoxes = 0
	for row in square:
		for pix in row:
			R = pix[0]
			G = pix[1]
			B = pix[2]
			if not(R == 0 and G == 0 and B == 0):
				Rtotal += R
				Gtotal += G
				Btotal += B
				numBoxes += 1
	Ravg = Rtotal // numBoxes
	Gavg = Gtotal // numBoxes
	Bavg = Btotal // numBoxes
	pixel = (Ravg, Gavg, Bavg)
	return pixel


"""test = [[(1,1,3),(2,2,3),(3,3,3)],
[(4,4,3),(5,5,3),(6,6,3)],
[(7,7,3),(8,8,3),(9,9,3)]]

print(compress2dArray(test))"""



	



