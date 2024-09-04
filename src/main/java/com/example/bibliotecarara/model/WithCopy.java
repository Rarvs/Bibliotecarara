package com.example.bibliotecarara.model;

import org.springframework.data.repository.cdi.Eager;

public abstract class WithCopy<E> {

    public abstract void updateWith(E copy);
}