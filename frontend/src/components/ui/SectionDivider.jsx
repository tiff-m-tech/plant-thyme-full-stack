export default function SectionDivider() {
    return (
        <div className="section-divider">
            <span className="divider-line"></span>
            <img
                src={`${import.meta.env.BASE_URL}images/brand/separator.png`}
                alt=""
                className="divider-ornament"
            />
            <span className="divider-line"></span>
        </div>
    );
}
