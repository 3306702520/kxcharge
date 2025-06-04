export function usePageScroll() {
  const scrollTop = ref(0)
  onPageScroll((e) => {
    scrollTop.value = e.scrollTop
  })

  return {
    scrollTop
  }
}