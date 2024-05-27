<script>
export default {
  data() {
    return {
      titleClass: 'title',
      vBindClass:'v-bind'
    }
  }
}
</script>

<template>
  <h1 :class="titleClass">{ ':${html property}' }</h1>
  <h1 v-bind:class="vBindClass"> ' v-bind:{html property}' </h1>
</template>

<style>
.title{
  color: deepskyblue;
}

.v-bind{
  color: coral;
}

</style>

