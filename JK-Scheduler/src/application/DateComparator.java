/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.time.LocalDateTime;
import java.util.Comparator;

/**
 *
 * @author Kyle
 */
public class DateComparator implements Comparator<Appointment> {

    @Override
    public int compare(Appointment first, Appointment second) {
        return first.compareTo(second);
    }
}
