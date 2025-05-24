package utils

object Validator {

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return email.matches(emailRegex)
    }

    fun isValidPhone(phone: String): Boolean {
        val phoneRegex = "^[+]?[0-9]{10,15}\$".toRegex()
        return phone.matches(phoneRegex)
    }

    fun isValidName(name: String): Boolean {
        val nameRegex = "^[A-Za-z]+\$".toRegex()
        return name.matches(nameRegex)
    }
}