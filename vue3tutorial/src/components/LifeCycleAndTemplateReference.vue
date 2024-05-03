<script>
export default {
  mounted() {
    this.$refs.p.textContent = '마운트 되었어요!'
    this.$refs.ff.textContent = '마운트 되었어요!'
  }
}
</script>

<template>
  <p ref="p">안녕</p>
  <h1 ref="ff"> title </h1>
</template>