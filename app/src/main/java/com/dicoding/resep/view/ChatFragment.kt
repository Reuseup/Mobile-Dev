package com.bangkit.resep.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.resep.R
import com.bangkit.resep.adapter.ChatAdapter
import com.bangkit.resep.viewmodel.ChatViewModel

class ChatFragment : Fragment() {

    private lateinit var viewModel: ChatViewModel
    private lateinit var messageAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val editTextMessage: EditText = view.findViewById(R.id.editTextMessage)
        val buttonSend: Button = view.findViewById(R.id.buttonSend)

        messageAdapter = ChatAdapter(emptyList())
        recyclerView.adapter = messageAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            messageAdapter = ChatAdapter(messages)
            recyclerView.adapter = messageAdapter
            recyclerView.scrollToPosition(messages.size - 1)
        }

        buttonSend.setOnClickListener {
            val message = editTextMessage.text.toString().trim()
            if (message.isNotEmpty()) {
                viewModel.sendMessage(message)
                editTextMessage.text.clear()
            }
        }
    }
}