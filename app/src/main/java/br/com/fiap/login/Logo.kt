import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.fiap.login.R

@Composable
fun Logo() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.image),
            contentDescription = "Logo",
            modifier = Modifier
                .size(400.dp) // Ajuste o tamanho da imagem aqui
                .align(Alignment.Center), // Centraliza a imagem dentro do Box
            contentScale = ContentScale.Fit
        )
    }
}
