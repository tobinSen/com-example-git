package com.uplooking.queue;

import com.uplooking.xml.UserDTO;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MainQueue {

    public static void main(String[] args) {
        PriorityQueue<UserDTO> queue = new PriorityQueue<>(Comparator.comparing(UserDTO::getAge));
    }
}
