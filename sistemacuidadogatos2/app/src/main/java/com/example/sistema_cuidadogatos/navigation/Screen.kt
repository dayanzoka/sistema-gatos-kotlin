package com.example.sistema_cuidadogatos.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Home : Screen("home_screen")

    // Tutores
    object TutorList : Screen("tutor_list")
    object TutorForm : Screen("tutor_form/{tutorId}") {
        fun createRoute(tutorId: Long? = 0L) = "tutor_form/${tutorId ?: 0L}"
    }

    // Gatos
    object CatList : Screen("cat_list")
    object CatDetail : Screen("cat_detail/{gatoId}") {
        fun createRoute(gatoId: Long) = "cat_detail/$gatoId"
    }
    object CatForm : Screen("cat_form/{gatoId}") {
        fun createRoute(gatoId: Long? = 0L) = "cat_form/${gatoId ?: 0L}"
    }

    // Tratamentos
    object TreatmentList : Screen("treatment_list/{gatoId}") {
        fun createRoute(gatoId: Long) = "treatment_list/$gatoId"
    }
    object TreatmentForm : Screen("treatment_form/{treatmentId}/{gatoId}") {
        fun createRoute(treatmentId: Long? = 0L, gatoId: Long) = "treatment_form/${treatmentId ?: 0L}/$gatoId"
    }

    // Saúde
    object HealthHistory : Screen("health_history/{gatoId}") {
        fun createRoute(gatoId: Long) = "health_history/$gatoId"
    }
    object HealthRecordForm : Screen("health_record_form/{recordId}/{gatoId}") {
        fun createRoute(recordId: Long? = 0L, gatoId: Long) = "health_record_form/${recordId ?: 0L}/$gatoId"
    }

    // API
    object ApiDemo : Screen("api_demo")
}