/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javahttpserver;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Antonio
 */
public class SimilarityCheck {

    public static double compareStrings(String stringA, String stringB) {
        return StringUtils.getJaroWinklerDistance(stringA, stringB);
    }
}
