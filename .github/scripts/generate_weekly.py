from pathlib import Path
import re
import sys

ROOT_README = Path("README.md")
TARGET_ROOT = Path("v2.2")


def sanitize(name: str) -> str:
    invalid = r'<>:"/\\|?*'
    for ch in invalid:
        name = name.replace(ch, "-")
    return name.strip()


def extract_week_block(text: str):
    pattern = re.compile(
        r"^###\s*🟨\s*(.+?)\s*$([\s\S]*?)(?=^###\s|^##\s|\Z)",
        re.MULTILINE
    )

    match = pattern.search(text)
    if not match:
        raise ValueError("README에서 '### 🟨 주차명' 형식의 블록을 찾을 수 없습니다.")

    title = match.group(1).strip()
    body = match.group(2)

    return title, body


def extract_links(body: str):
    links = []
    for line in body.splitlines():
        stripped = line.strip()
        if re.match(r"^\[.+\]\(.+\)\s*$", stripped):
            links.append(stripped)

    if not links:
        raise ValueError("주차 블록 안에서 문제 링크를 찾지 못했습니다.")

    return links


def build_readme(title: str, links: list[str]) -> str:
    lines = [
        f"# {title}",
        "",
        "## 필수 문제",
        ""
    ]

    for link in links:
        lines.append(f"- {link}")

    lines.append("")
    return "\n".join(lines)


def main():
    if not ROOT_README.exists():
        print("루트 README.md가 없습니다.")
        sys.exit(1)

    text = ROOT_README.read_text(encoding="utf-8")

    title, body = extract_week_block(text)
    title = sanitize(title)
    links = extract_links(body)

    target_dir = TARGET_ROOT / title
    target_dir.mkdir(parents=True, exist_ok=True)

    target_readme = target_dir / "README.md"
    new_content = build_readme(title, links)

    print(f"title: {title}")
    print(f"target_dir: {target_dir}")
    print(f"target_readme: {target_readme}")
    print(f"links_count: {len(links)}")
    print("links:")
    for link in links:
        print(f"  {link}")

    if target_readme.exists():
        old_content = target_readme.read_text(encoding="utf-8")
        if old_content == new_content:
            print("변경 사항 없음: 기존 README와 생성 내용이 동일함")
            return
        else:
            print("기존 README와 생성 내용이 다름 -> 업데이트 진행")
    else:
        print("기존 README 없음 -> 새로 생성")

    target_readme.write_text(new_content, encoding="utf-8")
    print(f"생성 완료: {target_readme}")


if __name__ == "__main__":
    main()
