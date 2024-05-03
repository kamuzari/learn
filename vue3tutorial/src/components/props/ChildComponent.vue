<script>
export default {
  name: "ChildComponent",
  props:{
    messageFromParent: String
  }
}
</script>

<template>
  <h2>{{ messageFromParent || '부모로부터 아직 전달받은게 없어욧!!'}}</h2>
</template>

