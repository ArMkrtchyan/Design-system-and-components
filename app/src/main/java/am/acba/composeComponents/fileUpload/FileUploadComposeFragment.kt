package am.acba.composeComponents.fileUpload

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class FileUploadComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        FileUploadScreen(title)
    }
}