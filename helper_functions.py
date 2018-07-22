from math import sqrt
from functools import reduce
import requests
from PIL import Image
from io import BytesIO
import numpy as np
from skimage.measure import compare_mse as mse
import numpy as np

arr = [[1,2,3,4],
       [5,6,7,8],
       [9,10,11,12],
       [13,14,15,16]]

arr1 = [[1,2,3,4,100],
       [5,6,7,8,101],
       [9,10,11,12,102],
       [13,14,15,16,103]]

arr2 = [[1,2,3,4,100],
       [5,6,7,8,101],
       [9,10,11,12,102],
       [13,14,15,16,103],
       [17,18,19,20,104]]


class ImageTransformer:
    def __init__(self):
        print("Starting PuzzleUploader...")



    # takes list of pixels and image width to make embedded list of rows
    def formatImageInto2dArray(self, width, pixels):
        splitImage = []
        for i in range(0, len(pixels), width):
            splitImage.append(pixels[i:i + (width - 1)])
        return splitImage

    # averages rgb values to smaller pixel number
    # numSquares must be a perfect square
    def compress2dArray(self, box):
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


class ArrayTransformer:

    def __init__(self):
        print("Starting ArrayTransformer...")

    def setPuzzleBoard(self, img_url, num_pieces):
        """

        :param img_url: url of the puzzle board image
        :param num_pieces: number of pieces to break into
        :return: array of boxes, all of which are compressed, representing the puzzle board
        """
        raw_img = requests.get(img_url)
        img = Image.open(BytesIO(raw_img.content))
        img_width, img_height = img.size
        rgb_array = list(img.getdata())
        formatted_rgb_array = self.formatImageInto2dArray(img_width, rgb_array)
        boxed_rgb_array = self.divide2dArrayIntoNSubArrays(formatted_rgb_array, num_pieces)
        compressed_rgb_boxes = []
        for box in boxed_rgb_array:
            compressed_rgb_boxes.append(self.compress2dArray(box))
        return compressed_rgb_boxes

    # takes list of pixels and image width to make embedded list of rows
    def formatImageInto2dArray(self, width, pixels):
        splitImage = []
        for i in range(0, len(pixels), width):
            splitImage.append(pixels[i:i + (width - 1)])
        return splitImage

    # averages rgb values to smaller pixel number
    # numSquares must be a perfect square
    def compress2dArray(self, box):
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

    def computeSimilarity(self, imgA, imgB):
        instanceImgA = []
        instanceImgB = []
        instanceImgA[:] = imgA
        instanceImgB[:] = imgB
        for item in instanceImgA:
            if item == [255, 255, 255]:
                ind = instanceImgA.index(item)
                del instanceImgA[ind]
                del instanceImgB[ind]

        imageA = np.array(instanceImgA)
        imageB = np.array(instanceImgB)

        return mse(imageA, imageB)

    # normalize mse vals
    def normalize(self, similarities):
        maximum = max(similarities)
        heats = [val / maximum for val in similarities]

        return heats


    """
    Divides a two dimensional array into N pieces of equal size. It will discard values in the edges of the 2d array if
    they do not fit.
    
    e.g. if there is a 5x5 2d array (arr2) and N=4, it will ignore the 5th column and the 5th row because the subarray size
    is 2x2
    """
    def divide2dArrayIntoNSubArrays(self, arr, N):
        [rows, cols] = self._getMiddleFactors(N)

        if type(arr) is not list:
            return "Error: input is not a list"
        length = len(arr)
        if length is 0:
            return []
        if type(arr[0]) is not list:
            return "Error: input is a 1d array not a 2d array"
        width = len(arr[0])
        # print("length: " + str(length))
        # print("rows: " + str(rows))
        # print("width: " + str(width))
        # print("cols: " + str(cols))
        rowInterval = int(length/rows)
        colInterval = int(width/cols)
        # print("row")
        # print(rowInterval)
        # print("col")
        # print(colInterval)
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

    """
    Returns all the factors of an integer n
    """
    def _factors(self, n):
            step = 2 if n%2 else 1
            return set(reduce(list.__add__,
                        ([i, n//i] for i in range(1, int(sqrt(n))+1, step) if n % i == 0)))

    """
    Given an integer, it returns the factors which are closest together.
    e.g. if n=16, it returs [4,4] since they are closer together than [2,8] or [1,16]
    """
    def _getMiddleFactors(self, n):
        sorted_factors = sorted(self._factors(n))
        l = 0
        r = len(sorted_factors)-1
        while (r-l >= 1):
            l+=1
            r-=1
        print("division")
        print(str(sorted_factors[l]) + " " + str(sorted_factors[r]))
        print()
        return [sorted_factors[l], sorted_factors[r]]

    def test4x4Arr(self):
        for row in arr1:
            print(row)

        arr = self.divide2dArrayIntoNSubArrays(arr1, 16)
        print(arr)

    def testPuzzleBoardImg(self):
        test = []
        for j in range(0, 1600):
            row = []
            for i in range(0, 2400):
                row.append(i)
            test.append(row)

        print(test[0])

        final = self.divide2dArrayIntoNSubArrays(test, 300)

        print("numBoxes: " + str(len(final)))
        print("Width: " + str(len(final[0])))
        print("Length: " + str(len(final[0][0])))







