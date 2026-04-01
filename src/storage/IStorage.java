package storage;

import java.util.Map;

import model.image.IImage;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class acts as the interface for the storage. This is used to store all the images loaded
 * and altered by the user. All saved images are then removed from storage.
 */
public interface IStorage {

  /**
   * Adds an image to the storage.
   * @param image the image to be stored.
   * @param key access key.
   */
  void addImage(IImage image, String key);

  /**
   * Removes image from storage.
   * @param key access key.
   */
  void removeImage(String key);

  /**
   * Retrieves image from storage.
   * @param key access key.
   */
  IImage retrieveImage(String key);

  /**
   * Retrieves all the images stored.
   * {@return a map of all images stored}
   */
  Map<String, IImage> getImageMap();

  /**
   * Checks if storage contains an image with specified name.
   * @param key access key.
   * {@return true if image is found else false}
   */
  boolean containsImage(String key);

  /**
   * Retrieves all the names of the images stored.
   * {@return a string of names of the images stored}
   */
  String storedImages(String message);
}
