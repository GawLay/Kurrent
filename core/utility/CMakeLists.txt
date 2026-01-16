cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_VERBOSE_MAKEFILE on)

set(can_use_assembler TRUE)
enable_language(ASM)


add_library( # Sets the name of the library.
        crypto_helper
        # Sets the library as a shared library.
        SHARED

        src/main/cpp/crypto_helper.cpp
)

# Searches for a specified prebuilt library and stores the path as a variable
find_library( # Sets the name of the path variable.
        log-lib
        # Specifies the name of the NDK library that you want CMake to locate.
        log)

target_link_libraries(
        # Specifies the target library.
        crypto_helper
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib} )