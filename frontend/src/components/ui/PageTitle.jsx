export default function PageTitle({ title, className = "" }) {
    const leftDecoImagePath = `${import.meta.env.BASE_URL}images/brand/left-deco-leaf.png`;
    const rightDecoImagePath = `${import.meta.env.BASE_URL}images/brand/right-deco-leaf.png`;

    return (
        <div className={`page-title-row ${className}`}>
            <img src={leftDecoImagePath} alt="" className="end-deco-image" />
            <h1 className="title">{title}</h1>
            <img src={rightDecoImagePath} alt="" className="end-deco-image" />
        </div>
    );
}
