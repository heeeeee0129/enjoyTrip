import Swal from "sweetalert2";
export const fail = (error) => {
  Swal.fire({
    title: "실패!",
    text: "문제가 발생헀습니다." + error,
    icon: "error",
    confirmButtonText: "OK",
  });
};
