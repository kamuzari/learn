<script>
export default {
  data() {
    return {
      count: 0
    }
  },
  methods: {
    increment() {
      this.count++;
    }
  }
}
</script>

<template>
  <!-- 이 버튼이 작동하도록 만들어 봅시다 -->
  <button v-on:click="increment">"v-on:click='${method_name}' "숫자 세기: {{ count }}</button>
  <h1><dasdas</h1>
  <button @click="increment">"@click=${method_name}" 단축 문법 -> 숫자 세기: {{ count }}</button>
</template>