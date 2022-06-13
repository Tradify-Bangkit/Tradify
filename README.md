# Tradify-Machine Learning

Welcome to Tradify-Machine Learning section. This project is a Product Based Bangkit Capstone Project Team C22-PS327.

# Member

- M2002F0123-Bryan Vergus S-Machine Learning
- M2224W2071-Wilda Nurjannah-Machine Learning

# Our Work

We're making a multi-class classification model to detect various Indonesian traditional foods.
Overall, there are already 8 classes we've trained:
1. Rendang
2. Putu Ayu
3. Bika Ambon
4. Pie Susu
5. Mochi
6. Kerak Telur
7. Pempek
8. Lapis Talas Bogor

Each class consist of 120 images that we split into 3 section: train, validation, and test.

# Tools We Used

- Python
- Tensorflow
- CutMix Generator
- Google Colab
- VGG-19 pre-trained Model

# How We Do It

Firstly we load the dataset that we've previously made. To build a model we should preprocess the data. We do several things such as rescaling (normalization), and augmentating the data twice. First using ImageGenerator from Keras and second using CutMix Generator. From ImageGenerator we costumized rotation, flip, zoom range and also shift. From CutMix Generator takes two image and label pairs to perform the augmentation.

After preprocessing we move to build the model.
To gain at least 80% accuracy we did several things, besides adding additional data augmentation we also used pretrained model, VGG-19 (because we have less training data). we choose this as the base model because it has similar layer to our first CNN model. 

we load the pretrained model & weights, freeze all layers, and then create a new model by adding our own fully connected layer & also adding dropout. we take the VGG-19 layers until the blok4_pool. For first model we get 89% accuracy and for the second model we get 82% accuracy.


# Run Our Model

- Costum Your Own Model
You need to clone our repo because it contains the dataset and also the model. After that you can costumize the directory according to your local/cloud repo.
you don't need to unzip the file because we've already put code to unzip it. Don't forget to install the pre-requirement (tensorflow) and finally you can use our model (we also already put the converter).

- Use Existing Model
There are 2 existed model on our repo that you can try. Go to the aimed saved_model folder and download the model. We provide several formats such as .tflite, .json, .h5, and .pb. For implementing on Android Studio you can choose the .tflite model. 

