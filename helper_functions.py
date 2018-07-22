from math import sqrt
from functools import reduce

"""
Input: One 2d array
Output: 4 2d arrays (from top to bottom, left ot right)
"""
def split2DArrayIntoFour1DArrays(arr, flag=True):

    if type(arr) is not list:
        return "Error: input is not a list"
    length = len(arr)
    if length is 0:
        return []
    if type(arr[0]) is not list:
        return "Error: input is a 1d array not a 2d array"
    width = len(arr[0])

    half_length = int(length/2)
    half_width = int(width/2)

    splicedArray = []

    # top left array
    splice = []
    for i in range(0, half_length):
        for j in range(0, half_width):
            splice.append(arr[i][j])
    splicedArray.append(splice)

    # top right array
    splice = []
    for i in range(half_length, length):
        for j in range(0, half_width):
            splice.append(arr[i][j])
    splicedArray.append(splice)

    # bottom left array
    splice = []
    for i in range(0, half_length):
        for j in range(half_width, width):
            splice.append(arr[i][j])
    splicedArray.append(splice)

    # bottom right array
    splice = []
    for i in range(half_length, length):
        for j in range(half_width, width):
            splice.append(arr[i][j])
    splicedArray.append(splice)


    return splicedArray


"""
Input: One 2d array
Output: 4 2d arrays
"""
def split2DArrayIntoFour2DArrays(arr):

    if type(arr) is not list:
        return "Error: input is not a list"
    length = len(arr)
    if length is 0:
        return []
    if type(arr[0]) is not list:
        return "Error: input is a 1d array not a 2d array"
    width = len(arr[0])

    half_length = int(length/2)
    half_width = int(width/2)

    splicedArray = []

    # top left array
    splice = []
    for i in range(0, half_length):
        temp = []
        for j in range(0, half_width):
            temp.append(arr[i][j])
        splice.append(temp)
    splicedArray.append(splice)

    # top right array
    splice = []
    for i in range(half_length, length):
        temp = []
        for j in range(0, half_width):
            temp.append(arr[i][j])
        splice.append(temp)
    splicedArray.append(splice)

    # bottom left array
    splice = []
    for i in range(0, half_length):
        temp = []
        for j in range(half_width, width):
            temp.append(arr[i][j])
        splice.append(temp)
    splicedArray.append(splice)

    # bottom right array
    splice = []
    for i in range(half_length, length):
        temp = []
        for j in range(half_width, width):
            temp.append(arr[i][j])
        splice.append(temp)
    splicedArray.append(splice)


    return splicedArray


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



def factors(n):
        step = 2 if n%2 else 1
        return set(reduce(list.__add__,
                    ([i, n//i] for i in range(1, int(sqrt(n))+1, step) if n % i == 0)))


def getMiddleFactors(n):
    sorted_factors = sorted(factors(n))
    l = 0
    r = len(sorted_factors)-1
    while (r-l >= 1):
        l+=1
        r-=1
    print("division")
    print(str(sorted_factors[l]) + " " + str(sorted_factors[r]))
    print()
    return [sorted_factors[l], sorted_factors[r]]


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


    print("length: " + str(length))
    print("rows: " + str(rows))
    print("width: " + str(width))
    print("cols: " + str(cols))

    i = 0
    j = 0

    currRowMin = i
    rowInterval = i + int(length/rows)
    print("row")
    print(rowInterval)
    currColMin = j
    colInterval = j + int(width/cols)
    print("col")


    print(colInterval)
    arrayOfBoxes = []
    box = []
    # for each row
    for currRowMin in range(0, length, rowInterval):
        for currColMin in range(0, width, colInterval):

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
                if (len(row_box) > 0):
                    box.append(row_box)
                else:
                    print("none in row")

            arrayOfBoxes.append(box)

    return arrayOfBoxes


for row in arr1:
    print(row)

arr = divide2dArrayIntoNSubArrays(arr1, 4)
print(arr)



test = []

for j in range(0, 1664):
    row = []
    for i in range(0, 2362):
        row.append(i)
    test.append(row)

print(test[0])

final = divide2dArrayIntoNSubArrays(test, 300)

print("Length: " + str(len(final)))
print("Width: " + str(len(final[0])))
print("Width2: " + str(len(final[0][0])))




