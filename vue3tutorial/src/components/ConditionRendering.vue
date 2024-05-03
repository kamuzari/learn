<script>
export default {
  data() {
    return {
      awesome: true
    }
  },
  methods: {
    toggle() {
      this.awesome = this.awesome ? false: true
    }
  }
}
</script>

<template>
  <button @click="toggle">토글 버튼</button>
  <h1 v-if="awesome">Vue는 굉장해! 엄청나!</h1>
  <h1 v-else>오 안돼 😢</h1>
</template>