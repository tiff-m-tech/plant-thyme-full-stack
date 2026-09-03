export function altFromFileName(imgName) {
    const fileName = imgName.split("/").pop();
    const removeImgType = fileName.replace(/\.[^.]+$/, "");
    const spaced = removeImgType.replace(/-/g, " ");
    const altImgValue = spaced
        .split(" ")
        .map((word) => word.charAt(0).toUpperCase() + word.slice(1))
        .join(" ");

    return altImgValue;
}
// split("/") separates the image path at each slash.
// pop() gets the filename from the end of the path, such as "large-logo.png".
// The first replace() removes the file extension, resulting in "large-logo".
// The second replace() changes every hyphen to a space, resulting in "large logo".
// map() capitalizes each word, resulting in "Large Logo".
