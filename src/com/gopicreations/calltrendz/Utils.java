/*
 *  Copyright Gopi Creations (http://www.gopicreations.com)
 */
package com.gopicreations.calltrendz;

import java.util.Map;

/**
 * Convenience methods
 * @author gopi
 *
 */
public class Utils {
  
  /**
   * Creates a matrix out of given values in the map. If any values are missing it adds rows with count value as zero.
   * Useful for creating data input for graphs etc.
   * Example -
   * 
   * Input 
   *  map      : {(2,30), (4,15), (5,50)}
   *  rowCount : 7
   *  startAt  : 1
   * 
   * Output
   *  matrix - {[1,0], [2,30], [3, 0], [4,15], [5,50], [6,0], [7,0]}
   *  
   *  Had the startAt value in the input been 0, the output would be -
   *  matrix - {[0,0], [1,30], [2, 0], [3,15], [4,50], [5,0], [6,0]}
   *  
   * @param map the input values map
   * @param rowCount number of rows expected in the output matrix
   * @param startAt starting index for the row
   * @return complete matrix as specified in the description
   */
  public static int[][] mapToMatrix(Map<Integer, Integer> map, int rowCount, int startAt) {
    int[][] matrix = new int[rowCount][2]; 
    for(int i=0,j=startAt; i<rowCount; i++,j++) {
      matrix[i][0] = j;
      matrix[i][1] = map.get(j)==null? 0 : map.get(j); 
    }
    return matrix;
  }
}
