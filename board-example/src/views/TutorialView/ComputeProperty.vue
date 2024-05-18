<script setup>
import {computed, ref} from 'vue'

let id = 0

const newTodo = ref('')
const hideCompleted = ref(false)
const todos = ref([
  {id: id++, text: 'vue tutorial 진행하기', done: true},
  {id: id++, text: 'design pattern 학습하기', done: true},
  {id: id++, text: '서비스 기업 최종합격하기', done: false}
])

function addTodo() {
  todos.value.push({id: id++, text: newTodo.value, done: false})
  newTodo.value = ''
  console.log(todos.value)
}

function removeTodo(todo) {
  todos.value = todos.value.filter((t) => t !== todo)
}

const filterTodos = computed(() => {
  console.log("compute!!!!!");
  console.log(`value -> ${hideCompleted.value}`)
  if (hideCompleted.value) {
    return todos.value.filter(todo => !todo.done)
  }else{
    return todos.value;
  }

})
</script>
<template>
  <h1> 6. 계산된 속성</h1>
  <form @submit.prevent="addTodo">
    <input v-model="newTodo">
    <button>Add Todo</button>
  </form>
  <ul>
    <li v-for="todo in filterTodos" :key="todo.id">
      <input type="checkbox" v-model="todo.done">
      <span :class="{ done: todo.done }">{{ todo.text }}</span>
      <button @click="removeTodo(todo)">X</button>
    </li>
  </ul>
  <button @click="hideCompleted = !hideCompleted">
    {{ hideCompleted ? 'Show all' : 'Hide completed' }}
  </button>
</template>

<style>
.done {
  text-decoration: line-through;
}
</style>