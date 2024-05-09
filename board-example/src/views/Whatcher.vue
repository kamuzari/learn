<script setup>
import {ref, watch} from 'vue'
import axios from "axios";

const todoId = ref(1)
const todoData = ref(null)

async function getMyTodos() {
  todoData.value = null
  if(todoId<0 || todoId>100){
    return
  }
  await axios.get(
      `https://jsonplaceholder.typicode.com/todos/${todoId.value}`
  ).then(response => todoData.value = response.data)
      .catch(error => console.log(error.data))
}

getMyTodos()
watch(todoId,getMyTodos)
</script>

<template>
  <p>할 일 id: {{ todoId }}</p>
  <button @click="todoId++">다음 할 일 가져오기</button>
  <button @click="todoId--">이전 할 일 가져오기</button>
  <p v-if="!todoData">로딩...</p>
  <pre v-else>{{ todoData }}</pre>
</template>