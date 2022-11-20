package com.example.yavi.demo;

import am.ik.yavi.core.ConstraintGroup;

enum Group implements ConstraintGroup {
    CREATE, DELETE;
    public String test() {
        return this.name();
    }
}