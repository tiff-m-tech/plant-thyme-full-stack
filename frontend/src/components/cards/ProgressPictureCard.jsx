export default function ProgressPictureCard({ src, date, onClick }) {
    return (
        <div className="progress-picture-card" onClick={onClick}>
            <div className="progress-picture-date">{date}</div>
            <img
                src={src}
                alt={`Progress picture of plant from ${date}`}
                className="progress-picture"
            />
        </div>
    );
}
