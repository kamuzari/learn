<script>
export default {
  data() {
    return {
      todoId: 1,
      todoData: null
    }
  },
  methods: {
    async fetchData() {
      this.todoData = null
      const res = await fetch(
          `https://jsonplaceholder.typicode.com/todos/${this.todoId}`
      )
      this.todoData = await res.json()
    }
  },
  watch: {
    todoId(newCount) {
      console.log('newCount value -> '+newCount);
      this.fetchData();
    }
  },
  mounted() {
    this.fetchData()
  }
}
</script>

<template>
  <p>할 일 id: {{ todoId }}</p>
  <button @click="todoId++">다음 할 일 가져오기</button>
  <p v-if="!todoData">로딩...</p>
  <pre v-else>{{ todoData }}</pre>
</template>