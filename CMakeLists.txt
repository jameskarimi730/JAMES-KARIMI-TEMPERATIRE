cmake_minimum_required(VERSION 3.22.1)
project(tempconvert C)

add_library(
    tempconvert
    SHARED
    temperature.c
)
