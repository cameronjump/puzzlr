"""
Input: One 2d array
Output: 4 2d arrays
"""
def split2DArrayIntoFour(arr):

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

x = split2DArrayIntoFour(arr2)
for i in x:
    print(i)
