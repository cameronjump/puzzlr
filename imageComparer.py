from skimage.measure import compare_mse as mse
import numpy as np

imgA = [[255, 255, 255], [1, 1, 1], [1, 1, 1], [1, 1, 1],[1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1],[1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1]]
imgB =[[102, 71, 77], [1, 78, 78], [102, 71, 77], [102, 71, 77],[102, 71, 77],[102, 71, 77],[102, 71, 77],[102, 71, 77],[102, 71, 77],[102, 71, 77],[102, 71, 77],[102, 71, 77]]


def computeSimilarity(imgA, imgB):
	for item in imgA:
		if item==[255,255,255]:
			ind = imgA.index(item)
			print(ind)
			del imgA[ind]
			del imgB[ind]

	imageA = np.array(imgA)
	imageB = np.array(imgB)

	return mse(imageA, imageB)

#normalize mse vals
def normalize(similarities):
	maximum = max(similarities)
	heats = [val / maximim for val in similarities]

	return heats

print(len(imgA))
computeSimilarity(imgA, imgB)
print(len(imgA))


