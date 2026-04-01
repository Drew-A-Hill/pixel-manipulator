package commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import commands.load.LoadCommand;
import commands.operations.BlueGreyScaleCommand;
import commands.operations.BrightenCommand;
import commands.operations.DarkenCommand;
import commands.operations.GreenGreyScaleCommand;
import commands.operations.IntensityGreyScaleCommand;
import commands.operations.LumaGreyScaleCommand;
import commands.operations.RedGreyScaleCommand;
import commands.operations.ValueGreyScaleCommand;
import commands.save.SaveCommand;
import storage.IStorage;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class holds all the commands in a hashmap for easy access and alteration.
 */
public class CommandMap {
  protected Map<String, ICommands> cmdMap;
  private final Scanner scanner;
  private IStorage storage;

  /**
   * Constructs a new instance of the command map that will be used to call the users desired
   * commands.
   * @param scanner contains all the required arguments to determine the command to run.
   * @param storage storage object that holds the images that have been loaded.
   */
  public CommandMap(Scanner scanner, IStorage storage) {
    if (scanner == null || storage == null) {
      throw new IllegalArgumentException("Storage/ Import error\n");
    }

    this.scanner = scanner;
    this.storage = storage;
    this.cmdMap = new HashMap<>();
    addCommands();
  }

  /**
   * Adds all the usable commands to the command map.
   */
  private void addCommands() {
    this.cmdMap.put("load", new LoadCommand(this.scanner, this.storage));
    this.cmdMap.put("save", new SaveCommand(this.scanner, this.storage));
    this.cmdMap.put("value", new ValueGreyScaleCommand(this.scanner, this.storage));
    this.cmdMap.put("luma", new LumaGreyScaleCommand(this.scanner, this.storage));
    this.cmdMap.put("red", new RedGreyScaleCommand(this.scanner, this.storage));
    this.cmdMap.put("green", new GreenGreyScaleCommand(this.scanner, this.storage));
    this.cmdMap.put("blue", new BlueGreyScaleCommand(this.scanner, this.storage));
    this.cmdMap.put("brighten", new BrightenCommand(this.scanner, this.storage));
    this.cmdMap.put("darken", new DarkenCommand(this.scanner, this.storage));
    this.cmdMap.put("intensity", new IntensityGreyScaleCommand(this.scanner, this.storage));
  }

  /**
   * Provides the command map for access to commands.
   * {@return the command map}
   */
  public Map<String, ICommands> getCmdMap() {
    return this.cmdMap;
  }
}
