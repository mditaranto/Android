package com.example.examenvacas.Entidades

data class tConfiguracion(
    var numVacas:Int,
    var numLitros:Int
    ) {
    companion object {
        val numVacas = 20
        val numLitros = 30
    }
}
