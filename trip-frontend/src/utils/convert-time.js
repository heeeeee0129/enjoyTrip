// export const formatDuration = (minutes) => {
//   const hours = Math.floor(minutes / 60);
//   const remainingMinutes = minutes % 60;
//   console.log(minutes);
//   return `${hours}시간 ${remainingMinutes}분`;
// };

export const formatDuration = (seconds) => {
  const totalMinutes = Math.floor(seconds / 60);
  const hours = Math.floor(totalMinutes / 60);
  const remainingMinutes = totalMinutes % 60;
  const result = hours
    ? `${hours}시간 ${remainingMinutes}분`
    : `${remainingMinutes}분`;
  return result;
};
