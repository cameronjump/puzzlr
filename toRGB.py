from PIL import Image
import requests
from io import BytesIO
import math
from math import sqrt
import numpy as np
from skimage.measure import compare_mse as mse
import numpy as np
from functools import reduce


# takes list of pixels and image width to make embedded list of rows
def formatImageInto2dArray(width, pixels):
	splitImage = []
	for i in range(0,len(pixels), width):
		splitImage.append(pixels[i:i+(width-1)])
	return splitImage

# averages rgb values to smaller pixel number
# numSquares must be a perfect square
def compress2dArray(box):
	Rtotal = 0
	Gtotal = 0
	Btotal = 0
	numBoxes = 0
	for row in box:
		for pix in row:
			R = pix[0]
			G = pix[1]
			B = pix[2]
			Rtotal += R
			Gtotal += G
			Btotal += B
			numBoxes += 1	
	Ravg = Rtotal // numBoxes
	Gavg = Gtotal // numBoxes
	Bavg = Btotal // numBoxes
	pixel = [Ravg, Gavg, Bavg]
	return pixel

def computeSimilarity(imgA, imgB):
	instanceImgA = []
	instanceImgB = []
	instanceImgA[:] = imgA
	instanceImgB[:] = imgB
	for item in instanceImgA:
		if item==[255,255,255]:
			ind = instanceImgA.index(item)
			del instanceImgA[ind]
			del instanceImgB[ind]


	imageA = np.array(instanceImgA)
	imageB = np.array(instanceImgB)

	return mse(imageA, imageB)

#normalize mse vals
def normalize(similarities):
	maximum = max(similarities)
	heats = [val / maximum for val in similarities]

	return heats

"""
Returns all the factors of an integer n
"""
def factors(n):
        step = 2 if n%2 else 1
        return set(reduce(list.__add__,
                    ([i, n//i] for i in range(1, int(sqrt(n))+1, step) if n % i == 0)))

"""
Given an integer, it returns the factors which are closest together.
e.g. if n=16, it returs [4,4] since they are closer together than [2,8] or [1,16]
"""
def getMiddleFactors(n):
    sorted_factors = sorted(factors(n))
    l = 0
    r = len(sorted_factors)-1
    while (r-l >= 1):
        l+=1
        r-=1

    return [sorted_factors[l], sorted_factors[r]]


"""
Divides a two dimensional array into N pieces of equal size. It will discard values in the edges of the 2d array if
they do not fit.
e.g. if there is a 5x5 2d array (arr2) and N=4, it will ignore the 5th column and the 5th row because the subarray size
is 2x2
"""
def divide2dArrayIntoNSubArrays(arr, N):
    [rows, cols] = getMiddleFactors(N)

    if type(arr) is not list:
        return "Error: input is not a list"
    length = len(arr)
    if length is 0:
        return []
    if type(arr[0]) is not list:
        return "Error: input is a 1d array not a 2d array"
    width = len(arr[0])


    rowInterval = int(length/rows)
    colInterval = int(width/cols)


    arrayOfBoxes = []
    for currRowMin in range(0, length, rowInterval):
        for currColMin in range(0, width, colInterval):

            # ignoring right edge and bottom edge values
            if currRowMin + rowInterval > length:
                continue
            if currColMin + colInterval > width:
                continue

            box = []
            for inner_row_idx in range(currRowMin, currRowMin + rowInterval):
                row_box = []
                for inner_col_idx in range(currColMin, currColMin + colInterval):
                    if inner_row_idx < length and inner_col_idx < width:
                        row_box.append(arr[inner_row_idx][inner_col_idx])
                    else:
                        print("Out of bounds error. row: " + str(inner_row_idx) + " col: " + str(inner_col_idx))
                if len(row_box) > 0:
                    box.append(row_box)
                else:
                    print("none in row")
            arrayOfBoxes.append(box)
    return arrayOfBoxes


piecepic = requests.get('http://res.cloudinary.com/puzzlr/image/upload/e_make_transparent:25/e_trim/ar_1:1,c_crop/piece.jpg')
piece = Image.open(BytesIO(piecepic.content))
pixelspiece = list(piece.getdata())
widthpiece, heightpiece = piece.size
puzzlepic = requests.get('http://res.cloudinary.com/puzzlr/image/upload/puzzle.jpg')
puzzle = Image.open(BytesIO(puzzlepic.content))
pixelspuzzle = list(puzzle.getdata())
widthpuzzle, heightpuzzle = puzzle.size
pieceFormatted = formatImageInto2dArray(widthpiece, pixelspiece)

puzzleFormatted = formatImageInto2dArray(widthpuzzle, pixelspuzzle)

boxesPiece = divide2dArrayIntoNSubArrays(pieceFormatted, 16)


boxesPuzzle = divide2dArrayIntoNSubArrays(puzzleFormatted, 300)

pieceArray = []


for box in boxesPiece:
	pieceArray.append(compress2dArray(box))




allPieces = []
for piece in boxesPuzzle:
	squashedPiece = []
	simplePiece = divide2dArrayIntoNSubArrays(piece, 16)
	for box in simplePiece:
		squashedPiece.append(compress2dArray(box))
	allPieces.append(squashedPiece)

similarities = []
for piece in allPieces:
	sindex = max(computeSimilarity(pieceArray, piece),computeSimilarity(pieceArray90, piece),
				 computeSimilarity(pieceArray180, piece),computeSimilarity(pieceArray270, piece))
	similarities.append(sindex)

heatmap = normalize(similarities)

print(heatmap)

	



