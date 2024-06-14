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
fun LoginForm() {
    val keyboardController = LocalSoftwareKeyboardController.current

    // States for holding email and password
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf("") }

    val registerTextStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        color = Color(0xFF3E3C3C)
    )

    val annotatedText = buildAnnotatedString {
        append("Acesse o seu e-mail loca")

        withStyle(style = SpanStyle(color = Color(0xFFD33434))) {
            append("Web")
        }

        append(" e \nveja agora quem te mandou e-mail")
    }

    fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"
        return Pattern.compile(emailRegex).matcher(email).matches()
    }


    fun validateFields(
        email: MutableState<String>,
        password: MutableState<String>,
    ): String? {
        return when {
            email.value.isEmpty() -> "E-mail é obrigatório"
            !isEmailValid(email.value) -> "Informe um e-mail válido"
            password.value.isEmpty() -> "Senha é obrigatória"
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
                    bottom = 50.dp,
                )
        )

        // Campo de email
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            modifier = Modifier
                .fillMaxWidth(),
                //.height(48.dp),
            label = { Text("E-mail") },
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

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de senha
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            modifier = Modifier
                .fillMaxWidth(),
                //.height(48.dp),
            label = { Text("Senha") },
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
                text = "Continuar Conectado",
                style = registerTextStyle,
                fontSize = 14.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start // Alinha o conteúdo à esquerda
        ) {
            Text(
                text = "Esqueceu a senha? ",
                style = registerTextStyle,
                fontSize = 14.sp
            )
            Text(
                text = AnnotatedString(
                    text = "Clique aqui",
                    spanStyles = listOf(
                        AnnotatedString.Range(
                            item = SpanStyle(
                                color = Color(0xFF3B5998),
                                textDecoration = TextDecoration.Underline
                            ),
                            start = 0,
                            end = "Clique aqui".length
                        )
                    )
                ),
                fontSize = 14.sp,
                modifier = Modifier.clickable {
                    // navController.navigate("nextPage")
                }
            )
        }


        Spacer(modifier = Modifier.height(64.dp))

        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = Color(0xFFD33434))
            Spacer(modifier = Modifier.height(4.dp))
        }

        // Botão de login
        Button(
            onClick = {
                val error = validateFields(email, password)
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
                .padding(horizontal = 16.dp), // Adiciona padding horizontal ao redor do botão
            contentPadding = PaddingValues(vertical = 16.dp), // Aumenta o espaço interno vertical
            shape = RoundedCornerShape(4.dp), // Define bordas menos arredondadas
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3B5998),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Login",
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
                text = "Não tem conta ainda? ",
                style = registerTextStyle,
                fontSize = 13.sp
            )
            Text(
                text = AnnotatedString(
                    text = "Clique aqui",
                    spanStyles = listOf(
                        AnnotatedString.Range(
                            item = SpanStyle(
                                color = Color(0xFF3B5998),
                                textDecoration = TextDecoration.Underline
                            ),
                            start = 0,
                            end = "Clique aqui".length
                        )
                    )
                ),
                fontSize = 13.sp,
                modifier = Modifier.clickable {
                    // navController.navigate("nextPage")
                }
            )
            Text(
                text = " e cadastre agora mesmo",
                style = registerTextStyle,
                fontSize = 13.sp
            )
        }
    }
}
