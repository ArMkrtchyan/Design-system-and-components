package am.acba.component.extensions

import android.view.LayoutInflater
import android.view.ViewGroup

typealias Inflater<VB> = (LayoutInflater, ViewGroup?, Boolean) -> VB