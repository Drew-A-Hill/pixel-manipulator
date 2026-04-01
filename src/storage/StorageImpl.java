package storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import model.image.IImage;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class acts as the implementation for the storage. This is used to store all the images
 * loaded and altered by the user. All saved images are then removed from storage.
 */
public class StorageImpl implements IStorage {
  private Map<String, IImage> imageHashMap;

  /**
   * Constructs a new storage object to be used.
   */
  public StorageImpl() {
    this.imageHashMap = new HashMap<>();
  }

  /**
   * Adds an image to the storage.
   * @param image the image to be stored.
   * @param key access key.
   */
  @Override
  public void addImage(IImage image, String key) {
    this.imageHashMap.put(key, image);
  }

  /**
   * Removes image from storage.
   * @param key access key.
   */
  @Override
  public void removeImage(String key) {
    this.imageHashMap.remove(key);
  }

  /**
   * Retrieves image from storage.
   * @param key access key.
   */
  @Override
  public IImage retrieveImage(String key) {
    return this.imageHashMap.get(key);
  }

  /**
   * Retrieves all the images stored.
   * {@return a map of all images stored}
   */
  @Override
  public Map<String, IImage> getImageMap() {
    return this.imageHashMap;
  }

  /**
   * Checks if storage contains an image with specified name.
   * @param key access key.
   * {@return true if image is found else false}
   */
  @Override
  public boolean containsImage(String key) {
    return this.imageHashMap.containsKey(key);
  }

  /**
   * Retrieves all the names of the images stored.
   * @param message message telling user why they are seeing the list of images.
   * {@return a string of names of the images stored}
   */
  @Override
  public String storedImages(String message) {
    Set<String> images = this.getImageMap().keySet();
    StringBuilder sb = new StringBuilder();
    sb.append(message).append(System.lineSeparator());

    for (String imageName : images) {
      sb.append(imageName);
      sb.append(System.lineSeparator());
    }
    return sb.toString();
  }
}
