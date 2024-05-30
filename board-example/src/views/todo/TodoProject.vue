<script setup>
import todoList from "./TodoList.vue";
import todoForm from "./TodoForm.vue";
import {useCounterStore} from "@/store/BoardStore.js";

const store = useCounterStore();
</script>

<template>
  <h1> 완료된 todo 갯수 : {{store.countDone}}</h1>
  <todoForm/>
  <todoList/>
</template>

