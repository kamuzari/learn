<script>
export default {
  data() {
    return {
      combinatedText: '',
      vmodelVar:''
    }
  },
  methods: {
    onInput(e) {
      this.combinatedText = e.target.value
    }
  }
}
</script>

<template>
  <input :value="combinatedText" @input="onInput" placeholder="여기에 입력하기">
  <p>{{ combinatedText }}</p>

  <input v-model="vmodelVar">
  <p>{{ vmodelVar}}</p>
</template>