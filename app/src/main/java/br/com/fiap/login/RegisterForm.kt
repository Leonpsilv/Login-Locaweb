import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.regex.Pattern

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RegisterForm() {
    val keyboardController = LocalSoftwareKeyboardController.current

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val birthDate = remember { mutableStateOf("") }
    val cpf = remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf("") }

    val registerTextStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        color = Color(0xFF3E3C3C)
    )

    val annotatedText = buildAnnotatedString {
        append("Cadastre o seu e-mail loca")

        withStyle(style = SpanStyle(color = Color(0xFFD33434))) {
            append("Web")
        }

        append(" e \ntenha em mãos o melhor e-mail da atualidade")
    }

    fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"
        return Pattern.compile(emailRegex).matcher(email).matches()
    }

    fun isBirthDateValid(birthDate: String): Boolean {
        val dateRegex = "^\\d{2}/\\d{2}/\\d{4}$"
        if (!Pattern.compile(dateRegex).matcher(birthDate).matches()) {
            return false
        }
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.isLenient = false
            sdf.parse(birthDate)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun isCPFValid(cpf: String): Boolean {
        if (cpf.length != 11 || cpf.all { it == cpf[0] }) return false

        val numbers = cpf.map { it.toString().toInt() }

        val dv1 = (0..8).map { (it + 1) * numbers[it] }.sum() % 11 % 10
        val dv2 = (0..9).map { it * numbers[it] }.sum() % 11 % 10

        return dv1 == numbers[9] && dv2 == numbers[10]
    }

    fun validateFields(
        email: MutableState<String>,
        password: MutableState<String>,
        confirmPassword: MutableState<String>,
        name: MutableState<String>,
        birthDate: MutableState<String>,
        cpf: MutableState<String>,
    ): String? {
        return when {
            email.value.isEmpty() -> "E-mail é obrigatório"
            !isEmailValid(email.value) -> "Informe um e-mail válido"
            password.value.isEmpty() -> "Senha é obrigatória"
            confirmPassword.value.isEmpty() -> "Confirme a sua senha"
            password.value != confirmPassword.value -> "As senhas não são compatíveis"
            name.value.isEmpty() -> "Nome é obrigatório"
            birthDate.value.isEmpty() -> "Data de nascimento é obrigatório"
            !isBirthDateValid(birthDate.value) -> "Data inválida. Siga o exemplo: 01/01/2000"
            cpf.value.isEmpty() -> "CPF é obrigatório"
            !isCPFValid(cpf.value) -> "CPF inválido"
            else -> null
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = annotatedText,
            style = registerTextStyle,
            modifier = Modifier
                .padding(
                    top = 8.dp,
                    bottom = 16.dp,
                )
        )

        // Campo de nome
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            modifier = Modifier
                .fillMaxWidth(),
                //.height(48.dp),
            label = { Text("Nome") },
            isError = name.value.isEmpty(),
            textStyle = TextStyle(
                color = Color(0xFF3E3C3C),
                fontSize = 15.sp,
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions { keyboardController?.hide() },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            colors = outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF575454),
                unfocusedBorderColor = Color(0xFFC3B5B5),
                focusedLabelColor = Color(0xFF575454),
                unfocusedLabelColor = Color(0xFFA9A9A9)
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Campo de cpf
        OutlinedTextField(
            value = cpf.value,
            onValueChange = { cpf.value = it },
            modifier = Modifier
                .fillMaxWidth(),
                //.height(48.dp),
            label = { Text("CPF") },
            isError = !isCPFValid(cpf.value) && cpf.value.isNotEmpty(),
            textStyle = TextStyle(
                color = Color(0xFF3E3C3C),
                fontSize = 15.sp,
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions { keyboardController?.hide() },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            colors = outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF575454),
                unfocusedBorderColor = Color(0xFFC3B5B5),
                focusedLabelColor = Color(0xFF575454),
                unfocusedLabelColor = Color(0xFFA9A9A9)
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Campo de data de nascimento
        OutlinedTextField(
            value = birthDate.value,
            onValueChange = { birthDate.value = it },
            modifier = Modifier
                .fillMaxWidth(),
                //.height(48.dp),
            label = { Text("Data de nascimento") },
            isError = !isBirthDateValid(birthDate.value) && birthDate.value.isNotEmpty(),
            textStyle = TextStyle(
                color = Color(0xFF3E3C3C),
                fontSize = 15.sp,
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions { keyboardController?.hide() },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            colors = outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF575454),
                unfocusedBorderColor = Color(0xFFC3B5B5),
                focusedLabelColor = Color(0xFF575454),
                unfocusedLabelColor = Color(0xFFA9A9A9)
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Campo de email
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            modifier = Modifier
                .fillMaxWidth(),
                //.height(48.dp),
            label = { Text("E-mail") },
            isError = !isEmailValid(email.value) && email.value.isNotEmpty(),
            textStyle = TextStyle(
                color = Color(0xFF3E3C3C),
                fontSize = 15.sp,
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions { keyboardController?.hide() },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            colors = outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF575454),
                unfocusedBorderColor = Color(0xFFC3B5B5),
                focusedLabelColor = Color(0xFF575454),
                unfocusedLabelColor = Color(0xFFA9A9A9)
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Campo de senha
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            modifier = Modifier
                .fillMaxWidth(),
                //.height(48.dp),
            label = { Text("Senha") },
            isError = password.value.isEmpty(),
            textStyle = TextStyle(
                color = Color(0xFF3E3C3C),
                fontSize = 15.sp,
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions { keyboardController?.hide() },
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            colors = outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF575454),
                unfocusedBorderColor = Color(0xFFC3B5B5),
                focusedLabelColor = Color(0xFF575454),
                unfocusedLabelColor = Color(0xFFA9A9A9)
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Campo de confirmar senha
        OutlinedTextField(
            value = confirmPassword.value,
            onValueChange = { confirmPassword.value = it },
            modifier = Modifier
                .fillMaxWidth(),
               // .height(48.dp),
            label = { Text("Confirmar senha") },
            isError = password.value != confirmPassword.value && confirmPassword.value.isNotEmpty(),
            textStyle = TextStyle(
                color = Color(0xFF3E3C3C),
                fontSize = 15.sp,
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions { keyboardController?.hide() },
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            colors = outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF575454),
                unfocusedBorderColor = Color(0xFFC3B5B5),
                focusedLabelColor = Color(0xFF575454),
                unfocusedLabelColor = Color(0xFFA9A9A9)
            )
        )

        // checkbox
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start // Alinha o conteúdo à esquerda
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF3B5998),
                    uncheckedColor = Color(0xFFA9A9A9),
                    checkmarkColor = Color.White
                )
            )
            Text(
                text = "Receber avisos via e-mail sobre pessoas/organizações ajudadas por você?",
                style = registerTextStyle,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = Color(0xFFD33434))
            Spacer(modifier = Modifier.height(4.dp))
        }

        // Botão de registrar
        Button(
            onClick = {
                val error = validateFields(email, password, confirmPassword, name, birthDate, cpf)
                if (error == null) {
                    // Todos os campos estão preenchidos e válidos
                    // Executar lógica de submissão
                    errorMessage = ""
                } else {
                    // Mostrar mensagem de erro
                    errorMessage = error
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            shape = RoundedCornerShape(4.dp),
            enabled = true,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3B5998),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Realizar cadastro",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center // Alinha o conteúdo à esquerda
        ) {
            Text(
                text = "Já possui uma conta? ",
                style = registerTextStyle,
                fontSize = 13.sp
            )
            Text(
                text = AnnotatedString(
                    text = "Faça o login.",
                    spanStyles = listOf(
                        AnnotatedString.Range(
                            item = SpanStyle(
                                color = Color(0xFF3B5998),
                                textDecoration = TextDecoration.Underline
                            ),
                            start = 0,
                            end = "Faça o login.".length
                        )
                    )
                ),
                fontSize = 13.sp,
                modifier = Modifier.clickable {
                    // navController.navigate("nextPage")
                }
            )
        }
    }
}
