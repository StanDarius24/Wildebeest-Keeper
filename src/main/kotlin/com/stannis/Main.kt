package com.stannis

import com.stannis.parser.fileHandler.LogicHandler

fun main() {

    val projectPath =
        "C:\\Users\\Stannis\\Desktop\\KotlinLicenta\\src\\main\\resources\\c++\\project64-develop"

    LogicHandler.run(projectPath, listOf("oop"))
    /*
    -oop
    generates a serialized file with the important things from an oop
    point of view.

    - parse
     the files and the hierarchy of project directories are generated.
     .json files contain tokens generated by cdt

     */
}
