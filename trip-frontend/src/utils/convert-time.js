export const formatDuration = (seconds) => {
  const totalMinutes = Math.floor(seconds / 60);
  const hours = Math.floor(totalMinutes / 60);
  const remainingMinutes = totalMinutes % 60;
  const result = hours
    ? `${hours}시간 ${remainingMinutes}분`
    : `${remainingMinutes}분`;
  return result;
};

export const formatNumber = (number) => {
  let parts = number.toString().split(".");
  parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  return parts.join(".");
};
