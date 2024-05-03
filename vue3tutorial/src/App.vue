<script>
export default {
  data() {
    return {
      message: '안녕 Vue!',
      counter: {
        count: 0
      }
    }
  }
}
</script>

<template>
  <h1> {{ message}} </h1>
  <p>숫자 세기: {{counter.count}}</p>
</template>

